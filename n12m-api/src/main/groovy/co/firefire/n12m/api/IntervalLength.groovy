// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

enum IntervalLength {

  IL_15(15),
  IL_30(30)

  Integer id

  IntervalLength(Integer id) {
    this.id = id
  }

  Integer getMinute() {
    id
  }
  
  static String fromString(String str) {
    if (str != null || str.isInteger()) {
      IntervalLength.values().find { it.id == Integer.parseInt(str) }
    }
    else {
      throw new Exception("Given string [$str] is not a valid IntervalLength")
    }
  }
}
