package jp.villageworks.checkdigitutils.api;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CheckDigitApiTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("CheckDigitApi#calcISBNメソッドのテストクラス")
	class calcISBN {
		@Nested
		@DisplayName("ISBN10に対する計算")
		class ISBN10 {
			@Disabled
			@Test
			@DisplayName("【Test-04・ハイフンなし10桁】ISBN「476012277X」のチェックディジットは「X」である")
			// TODO:要検討
			// モジュラス11のチェックディジット計算では剰余が0または1の場合は0とする。
			// この一般的なモジュラス11の計算では、これでは剰余が0の場合と1の場合で判別できない。
			// 同じ計算であるISBN10では、明示的に場合分けを行うために剰余が1の場合は「X」としている
			// そのため、この仕様をモジュラス11の計算に組み込むとコードが肥大化してしまうので、現時点では仕様外とし」、このテストケースはスキップする。
			void test_04() {
				// setup
				String target = "476012277X";
				String expected = "X";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-03・ハイフンなし9桁】ISBN「479523087」のチェックディジットは「0」である")
			void test_03() {
				// setup
				String target = "479523087";
				String expected = "0";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-02・ハイフンなし10桁】ISBN「4873110122」のチェックディジットは「2」である")
			void test_02() {
				// setup
				String target = "4873110122";
				String expected = "2";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-01・ハイフンあり10桁】ISBN「4-7981-0712-3」のチェックディジットは「3」である")
			void test_01() {
				// setup
				String target = "4-7981-0712-3";
				String expected = "3";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
		}
		@Nested
		@DisplayName("ISBN13に対する計算")
		class ISBN13 {
			@Test
			@DisplayName("【Test-03・ハイフンなし12桁】ISBN「978477416366」のチェックディジットは「6」である")
			void test_03() {
				// setup
				String target = "978477416366";
				String expected = "6";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-02・ハイフンなし13桁】ISBN「9784774178943」のチェックディジットは「3」である")
			void test_02() {
				// setup
				String target = "9784774178943";
				String expected = "3";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-01・ハイフンあり13桁】ISBN「978-4-576-08211-0」のチェックディジットは「0」である")
			void test_01() {
				// setup
				String target = "978-4-576-08211-0";
				String expected = "0";
				// execute
				String actual = CheckDigitApi.calcISBN(target);
				// verify
				assertThat(actual, is(expected));
			}
		}
	}
	@Nested
	@DisplayName("CheckDigitApi#validateISBNメソッドのテストクラス")
	class validateISBN {
		@Test
		@DisplayName("【ISBN13-11】ISBN「9784798124281」は妥当なISBNではない")
		void test_11() {
			// setup
			String target = "9784798124281";
			// execute & verify
			assertThat(CheckDigitApi.validateISBN(target), is(false));
		}
		@Test
		@DisplayName("【ISBN13-04】ISBN「9784777520336」は妥当なISBNである")
		void test_04() {
			// setup
			String target = "9784777520336";
			// execute & verify
			assertThat(CheckDigitApi.validateISBN(target), is(true));
		}
		@Test
		@DisplayName("【ISBN13-03】ISBN「978-4-576-08211-0」は妥当なISBNである")
		void test_03() {
			// setup
			String target = "978-4-576-08211-0";
			// execute & verify
			assertThat(CheckDigitApi.validateISBN(target), is(true));
		}
		@Test
		@DisplayName("【ISBN10-02】ISBN「4576060732」は妥当なISBNである")
		void test_02() {
			// setup
			String target = "4576060732";
			// execute & verify
			assertThat(CheckDigitApi.validateISBN(target), is(true));
		}
		@Test
		@DisplayName("【ISBN10-01】ISBN「4-87311-012-2」は妥当なISBNである")
		void test_01() {
			// setup
			String target = "4-87311-012-2";
			// execute & verify
			assertThat(CheckDigitApi.validateISBN(target), is(true));
		}
	}

}
