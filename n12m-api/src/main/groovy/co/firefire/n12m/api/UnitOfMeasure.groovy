// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

enum UnitOfMeasure {

  KWH('kWh', 'kilowatt-hour')

  String shortDesc
  String longDesc

  UnitOfMeasure(String shortDesc, String longDesc) {
    this.shortDesc = shortDesc
    this.longDesc = longDesc
  }
}
