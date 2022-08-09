package jp.villageworks.checkdigitutils.api;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CodeValiaterTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("CodeValiater#validatISBNメソッドのテストクラス")
	class validateISBN {
		@Test
		@DisplayName("【Test-35・書式種検査のテスト】文字列「978476012X277X」はISBNの形式ではない")
		void test_35() {
			// setup
			String target = "978476012X277X";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(false));
		}
		@Test
		@DisplayName("【Test-34・書式種検査のテスト】文字列「47601227XX」はISBNの形式ではない")
		void test_34() {
			// setup
			String target = "47601227XX";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(false));
		}
		@Test
		@DisplayName("【Test-33・書式種検査のテスト】文字列「476012277X」はISBNの形式である")
		void test_33() {
			// setup
			String target = "476012277X";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-32・書式検査のテスト】文字列「978-4-576-08211-0」はISBNの形式である")
		void test_32() {
			// setup
			String target = "978-4-576-08211-0";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verify
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-31・書式検査のテスト】文字列「4-576-06073-2」はISBNの形式である")
		void test_31() {
			// setup
			String target = "4-576-06073-2";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verify
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-24・文字種検査のテスト】文字列「4-7981@0712-3」はISBNの形式ではない")
		void test_24() {
			// setup
			String target = "4-7981@0712-3";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(false));
		}
		@Test
		@DisplayName("【Test-23・文字種検査のテスト】文字列「476012277X」はISBNの形式である")
		void test_23() {
			// setup
			String target = "476012277X";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-22・文字種検査のテスト】文字列「978-4-576-08211-0」はISBNの形式である")
		void test_22() {
			// setup
			String target = "978-4-576-08211-0";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-21・文字種検査のテスト】文字列「4-576-06073-2」はISBNの形式である")
		void test_21() {
			// setup
			String target = "4-576-06073-2";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-14・文字数検査のテスト】文字列「444555666777888」はISBNの形式ではない")
		void test_14() {
			// setup
			String target = "444555666777888";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(false));
		}
		@Test
		@DisplayName("【Test-13・文字数検査のテスト】文字列「476012277X」はISBNの形式である")
		void test_13() {
			// setup
			String target = "476012277X";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-12・文字数検査のテスト】文字列「978-4-576-08211-0」はISBNの形式である")
		void test_12() {
			// setup
			String target = "978-4-576-08211-0";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
		@Test
		@DisplayName("【Test-11・文字数検査のテスト】文字列文字列「4-576-06073-2」はISBNの形式である")
		void test_11() {
			// setup
			String target = "4-576-06073-2";
			// execute
			boolean actual = CheckDigitApiExtends.useValidateISBN(target);
			// verfy
			assertThat(actual, is(true));
		}
	}
}

/**
 * テスト補助クラス：protecedメソッドのテスト用クラス
 * @author tutor
 */
class CheckDigitApiExtends extends CheckDigitApi {
	/**
	 * {@link CheckDigitApi#validateIsbnFormat(String)}メソッドを呼び出すだけのラッパーメソッド。
	 */
	public static boolean useValidateISBN(String target) {
		return validateIsbnFormat(target);
	}

}
