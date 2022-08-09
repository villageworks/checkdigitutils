package jp.villageworks.checkdigitutils.api;

import jp.villageworks.checkdigitutils.core.CheckDigitCore;

/**
 * チェックディジットに関する処理の公開メソッドクラス
 * @author tutor
 */
public class CheckDigitApi {

	/** クラス定数 */
	public static final int MODE_ISBN10_FOR_CALC = 9;
	public static final int MODE_ISBN10          = 10;
	public static final int MODE_ISBN13_FOR_CALC = 12;
	public static final int MODE_ISBN13          = 13;
	public static final boolean FOR_CALC         = true;
	
	public static final String PATTERN_SIZE_ISBN10    = "^(.){9,10}$";
	public static final String PATTERN_SIZE_ISBN13    = "^(.){12,13}$";
	public static final String PATTERN_CHAR_TYPE_ISBN = "^[0-9X]+$";
	public static final String PATTERN_FORMAT_ISBN    = "^(4|978)([0-9]+)([0-9X])$";
	
	/**
	 * 計算モードを定義した列挙型
	 * @author tutor
	 */
	private enum CalcMode {
		ISBN10(11, 2, 10),	// モジュラス11 ウェイト10-2
		ISBN13(10, 3, 1);		// モジュラス10 ウェイト3-1

		private int modulus;
		private int minWeight;
		private int maxWeight;

		private CalcMode(int modulus, int minWeight, int maxWeight) {
			this.modulus = modulus;
			this.minWeight = minWeight;
			this.maxWeight = maxWeight;
		}

		public int getModulus() {
			return modulus;
		}

		public int getMinWeight() {
			return minWeight;
		}

		public int getMaxWeight() {
			return maxWeight;
		}

	}

	/**
	 * ISBNのチェックディジットを計算する。
	 * @param target 計算対象のISBNコード
	 * @return チェックディジット 計算対象のISBNコードが妥当な形式でない場合はnull
	 */
	public static String calcISBN(String target) {

		// 計算対象のISBNコードの妥当性チェックを実施
		if (!validateIsbnFormat(target)) return null;
		// チェックディジット計算のためのISBNコードの正規化
		String normalizedIsbn = normalize(target, FOR_CALC);

		// 計算モード識別子：ISBN10に対する処理とISBN13に対する計算モードの識別子（実際には正規化した文字列の文字数で判断）
		int modeIdentifier = normalizedIsbn.length();
		// 処理識別子により計算モードを決定
		CalcMode mode = switchMode(modeIdentifier);
		// 計算モードに対応したモジュラスとウェイトを設定
		int modulus   = mode.getModulus();
		int minWeight = mode.getMinWeight();
		int maxWeight = mode.getMaxWeight();

		// チェックディジットの計算
		int checkDigit = CheckDigitCore.calcModulus(normalizedIsbn, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
		// チェックディジットを返却
		return Integer.valueOf(checkDigit).toString();
	}

	/**
	 * ISBNの書式を検査する。
	 * @param target 検査対象文字列
	 * @return すべてeの検査にパスした場合はtrue、それ以外はfalse
	 * 				 検査-1：文字数検査　10桁（ISBN10に対応）または13桁（ISBN13に対応）かどうかを検査する。
	 * 							 それぞれチェックディジットを含まない場合を考慮して9桁と12桁もtrueとする。
	 * 				 検査-2：文字種検査　数字とXで構成されているかどうかを検査する。
	 * 							 ISBN10ではチェックディジットとしてXが含まれる場合はtrueとする。
	 * 				 検査-3：書式検査　４または978で始まり末尾が数字またはXとなってるかどうかを検査する。
	 * 							 先頭文字と末尾文字の間に含まれる文字は数字以外が含まれる場合はfalseとする。
	 */
	protected static boolean validateIsbnFormat(String target) {
		// 【検査対象文字列の正規化】ハイフンを削除
		String inspectiveTarget = target.replaceAll("-", "");
		// 【検査-1：文字数検査】9か10桁の文字列または12か13桁の文字列であるかどうかをチェック
		if (!(inspectiveTarget.matches(PATTERN_SIZE_ISBN10) || inspectiveTarget.matches(PATTERN_SIZE_ISBN13))) return false;
		// 【検査-2：文字種検査】数字またはXで構成されているかどうかをチェック
		if (!inspectiveTarget.matches(PATTERN_CHAR_TYPE_ISBN)) return false;
		// 【検査-3：書式検査】4または978で始まり末尾の文字が数字またはXであるかどうかをチェック（中間はすべて数字）
		if (!inspectiveTarget.matches(PATTERN_FORMAT_ISBN)) return false;
		// 戻り値はtrue
		return true;
	}

	/**
	 * 指定されたISBNコードの書式およびチェックデジットが妥当であるかを判定する。
	 * @param target 判定対象のISBNコード
	 * @return 書式およびチェックディジットが正しい場合はtrue、それ以外はfalse
	 */
	public static boolean validateISBN(String target) {
		// 検査対象文字列の正規化
		String normalized = normalize(target, !FOR_CALC);
		// 計算モード識別子：ISBN10に対する処理とISBN13に対する計算モードの識別子（実際には正規化した文字列の文字数で判断）
		int modeIdentifier = normalized.length();
		// 処理識別子により計算モードを決定
		CalcMode mode = switchMode(modeIdentifier);
		// 数字列の重み付き合計を計算
		int sum = sumupFactory(modeIdentifier, mode.getMinWeight(), normalized);
		// 重み付き合計から剰余を計算：計算モードのモジュラスが除算子
		int remainder = sum % mode.getModulus();
		// 剰余による戻り地を切り替え
		if (remainder > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 計算モード識別子により計算モードを切り替える。
	 * @param modeIdentifier 計算モード識別子
	 * @return 計算モード識別子に対応した計算モード
	 */
	private static CalcMode switchMode(int modeIdentifier) {
		CalcMode mode = null;
		switch (modeIdentifier) {
		case MODE_ISBN10_FOR_CALC:
		case MODE_ISBN10:
			mode = CalcMode.ISBN10;
			break;
		case MODE_ISBN13_FOR_CALC:
		case MODE_ISBN13:
			mode = CalcMode.ISBN13;
			break;
		default:
			break;
		}
		return mode;
	}

	/**
	 * 数字列の重み付き合計を計算する。
	 * @param modeIdentifier 計算モード識別子
	 * @param weight 重み（ISBN10の場合は奇数インデックスの要素に対する乗数、ISBN13の場合は重みの開始数）
	 * @param target 処理対象となる数字列
	 * @return 重み付きの合計
	 */
	private static int sumupFactory(int modeIdentifier, int weight, String target) {
		// 数字列の各桁を格納する配列の生成
		String[] chars = target.split("");
		// 各桁の配列の各要素に対する処理：
		int sum = 0;
		for (int i = 0; i < modeIdentifier; i++) {
			// 末尾要素から処理を実施するため要素を参照するインデックスはインデックスの最大値から読み込んでいるループインデックスを引いた差を現在の要素のインデックスとしている
			int number = Integer.parseInt(chars[modeIdentifier - 1 - i]);
			switch (modeIdentifier) {
			case MODE_ISBN10:
				if (i == 0) {
					// 開始要素はチェックディジットなので重みなし
					sum += number;
				} else {
					// 開始要素以外は重みを乗じる
					sum += number * weight;
					// 重みは加算
					weight++;
				}
				continue;
			case MODE_ISBN13:
				if (i % 2 == 0) {
					// チェックディジットを含めた偶数インデックスの要素は重みなし
					sum += number;
				} else {
					// 奇数要素は重みを乗じる：重みは固定
					sum += number * weight;
				}
				continue;
			default:
				continue;
			}
		}
		// 合計を返却
		return sum;
	}

	/**
	 * チェックディジット計算の対象文字列を正規化する。
	 * @param target 計算対象の数字列
	 * @return 区切り文字などの数字以外の文字を削除された数字列
	 */
	@SuppressWarnings("unused")
	private static String normalize(String target) {
		return normalize(target, true);
	}

	/**
	 * チェックディジット計算のための正規化を実施する。
	 * @param target 計算対象の数字列
	 * @param forCalc チェックディジットを含めない正規化の場合はtrue、それ以外はfalse
	 * @return 区切り文字などの数字以外の文字を削除された数字列
	 * 				 チェックディジットを計算するための正規化の場合は末尾の数字（チェックデジット）の桁を除いた数字列、それ以外の場合は計算対象の文字列の数部分だけの数字列
	 */
	private static String normalize(String target, boolean forCalc) {
		// ハイフンを削除
		String normalized = target.replaceAll("-", "");
		// データサイズの統一：末尾桁のチェックデジットが含まれる場合（10桁または13桁の場合）は削除
		if (forCalc && (normalized.length() == 10 || normalized.length() == 13)) {
			normalized = normalized.substring(0, normalized.length() - 1);
		}
		// 正規化された文字列を返却
		return normalized;
	}

}
