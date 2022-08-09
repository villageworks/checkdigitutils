package jp.villageworks.checkdigitutils.core;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CheckDigitCoreTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("CheckDigitCore#calcDivideRemainderメソッドのテストクラス")
	class calcDivideRemainder {
		@Nested
		@DisplayName("ナインチェックによる計算")
		class nineCheck {
			private int divide = 9;
			@Test
			@DisplayName("9DSR：数字列「20151119」のチェックディジットは7である")
			void test_02() {
				// setup
				String target = "20151119";
				int expected = 7;
				// execute
				int actual = CheckDigitCore.calcDivideRemainder(target, divide, CheckDigitCore.IS_SUBTRACT);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("9DR：数字列「20151119」のチェックディジットは２である")
			void test_01() {
				// setup
				String target = "20151119";
				int expected = 2;
				// execute
				int actual = CheckDigitCore.calcDivideRemainder(target, divide, !CheckDigitCore.IS_SUBTRACT);
				// verify
				assertThat(actual, is(expected));
			}
		}
		@Nested
		@DisplayName("セブンチェックによる計算")
		class sevenCheck {
			private int divide = 7;
			@Test
			@DisplayName("7DSR：数字列「20151119」のチェックディジットは5である")
			void test_02() {
				// setup
				String target = "20151119";
				int expected = 5;
				// execute
				int actual = CheckDigitCore.calcDivideRemainder(target, divide, CheckDigitCore.IS_SUBTRACT);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("7DR：数字列「20151119」のチェックディジットは2である")
			void test_01() {
				// setup
				String target = "20151119";
				int expected = 2;
				// execute
				int actual = CheckDigitCore.calcDivideRemainder(target, divide, !CheckDigitCore.IS_SUBTRACT);
				// verify
				assertThat(actual, is(expected));
			}
		}
	}
	@Nested
	@DisplayName("CheckDigitCore#calcModulusメソッドのテストクラス")
	class calcModulus {
		@Nested
		@DisplayName("モジュラス11による計算")
		class modulus11 {
			private int modulus = 11;
			@Nested
			@DisplayName("ウェイト1-0による計算")
			class weight10 {
				private int minWeight = 1;
				private int maxWeight = 0;
				@Test
				@DisplayName("数字列「20151129」のチェックディジットは0である（剰余が1の場合）")
				void test_03() {
					// setup
					String target = "20151129";
					int expected  = 0;
					// xecute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, false);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151128」のチェックディジットは0である")
				void test_02() {
					// setup
					String target = "20151128";
					int expected = 0;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, false);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151119」のチェックディジットは1である")
				void test_01() {
					// setup
					String target = "20151119";
					int expected = 1;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, false);
					// verify
					assertThat(actual, is(expected));
				}
			}
			@Nested
			@DisplayName("ウェイト2-7による計算")
			class weight27 {
				private int minWeight = 2;
				private int maxWeight = 7;
				@Test
				@DisplayName("数字列「20152119」のチェックディジットは0である（剰余が1の場合）")
				void test_03() {
					// setup
					String target = "20152119";
					int expected = 0;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, false);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151219」のチェックディジットは0である")
				void test_02() {
					// setup
					String target = "20151219";
					int expected = 0;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, false);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151119」のチェックディジットは4である")
				void test_01() {
					// setup
					String target = "20151119";
					int expected = 4;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, false);
					// verify
					assertThat(actual, is(expected));
				}
			}
		}
		@Nested
		@DisplayName("モジュラス10による計算")
		class modulus10 {
			private int modulus = 10;
			@Nested
			@DisplayName("ウェイト3-1による計算")
			class weight31 {
				private int minWeight = 3;
				private int maxWeight = 1;
				@Test
				@DisplayName("数字列「20151129」のチェックディジットは9である")
				void test_02() {
					// setup
					String target = "20151129";
					int expected = 9;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151119」のチェックディジットは0である")
				void test_01() {
					// setup
					String target = "20151119";
					int expected = 0;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
			}
			@Nested
			@DisplayName("ウェイト2-1（分割）による計算")
			class weight21_ByLuhn {
				private int minWeight = 2;
				private int maxWeight = 1;
				@Test
				@DisplayName("数字列「20151149」のチェックディジットは0である")
				void test_02() {
					// setup
					String target = "20151149";
					int expected = 0;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151119」のチェックディジットは3である")
				void test_01() {
					// setup
					String target = "20151119";
					int expected = 3;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
			}
			@Nested
			@DisplayName("ウェイト2-1（一括）による計算")
			class wegiht21 {
				private int minWeight = 2;
				private int maxWeight = 1;
				@Test
				@DisplayName("文字列「ABC20151129」のチェックディジットは-99である")
				void test_13() {
					// setup
					String target = "ABC20151129";
					int expected = -99;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("空数字列「」のチェックディジットは-99である")
				void test_12() {
					// setup
					String target = "";
					int expected  = -99;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("nullのチェックディジットは-99である")
				void test_11() {
					// setup
					String target = null;
					int expected  = -99;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151169」のチェックディジットは0である")
				void test_02() {
					// setup
					String target = "20151169";
					int expected = 0;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
				@Test
				@DisplayName("数字列「20151119」のチェックディジットは5である")
				void test_01() {
					// setup
					String target = "20151119";
					int expected = 5;
					// execute
					int actual = CheckDigitCore.calcModulus(target, modulus, minWeight, maxWeight, !CheckDigitCore.BY_LUHN);
					// verify
					assertThat(actual, is(expected));
				}
			}
		}
	}
}
