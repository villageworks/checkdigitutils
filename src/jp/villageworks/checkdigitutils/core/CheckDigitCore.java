package jp.villageworks.checkdigitutils.core;

/**
 * チェックディジット計算クラス
 * @author tutor
 */
public class CheckDigitCore {

	/** クラス定数 */
	public static final boolean BY_LUHN = true;
	public static final boolean IS_SUBTRACT = true;

	/**
	 * モジュラスによってチェックディジットを計算する。
	 * @param target チェックディジットの計算対象となる数字列
	 * @param modulus モジュラス
	 * @param minWeight ウェイトの下限値
	 * @param maxWeight ウェイトの上限値
	 * @return チェックディジット：計算対象となる数字列がnullまたは空文字列である場合または数字以外の文字が含まれている場合は-99を返す。
	 */
	public static int calcModulus(String target, int modulus, int minWeight, int maxWeight, boolean byLuhn) {
		// 計算対象の引数（第一引数）のチェック
		if (target == null || target.isEmpty() || !target.matches("^[0-9]+$")) return -99;

		int coefficient = 1;
		String[] chars = target.split("");
		int sum = 0;
		for (int i = 0; i < chars.length; i++) {
			int number = Integer.valueOf(chars[chars.length - 1 - i]);

			if (maxWeight == 1) {
				// 要素のインデックスによる係数の切替え
				if (i % 2 == 0) {
					// インデックスが偶数の場合
					coefficient = minWeight;
				} else {
					// インデックスが奇数の場合
					coefficient = maxWeight;
				}
			} else {
				if (i == 0 || coefficient == maxWeight) {
					coefficient = minWeight;
				} else {
					coefficient++ ;
				}
			}
			int product = number * coefficient;

			if (byLuhn && product > 9) {
				// 合計が2桁になる場合の処理：各桁の数の合計を新たに合計とする
				product = product / 10 + product % 10;
			}
			sum += product;
		}
		// 合計に対するモジュラスによる剰余
		int remainder = sum % modulus;
		int checkDigit = -1;
		// 剰余の値によるチェックディジットの決定
		if (remainder == 0) {
			// 剰余が0の場合
			checkDigit = 0;
		} else if (modulus == 11 && remainder == 1) {
			checkDigit = 0;
		} else {
			// 剰余が0ではない場合
			checkDigit = modulus - remainder;
		}
		return checkDigit;
	}

	/**
	 * 指定された除数による剰余算によってチェックディジットを計算する。
	 * @param target チェックディジットの計算対象となる数字列
	 * @param divide 剰余算に使用する除数
	 * @param isSubtract 除数と剰余の差をチェックディジットとする場合はtrue、それ以外はfalse
	 * @return チェックディジット
	 */
	public static int calcDivideRemainder(String target, int divide, boolean isSubtract) {
		long number = Long.valueOf(target);
		int remainder = (int) (number % divide);
		int checkDigit = remainder;
		if (isSubtract) checkDigit = divide - remainder;
		return checkDigit;
	}
}
