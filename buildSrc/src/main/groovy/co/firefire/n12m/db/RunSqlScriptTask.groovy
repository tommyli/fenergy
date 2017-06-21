// Tommy Li (tommy.li@firefire.co), 2017-02-09

package co.firefire.n12m.db

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class RunSqlScriptTask extends DefaultTask {

  String driver = project.property('n12m.db.driver')
  String url
  String userid
  String password
  List<String> scripts = []

  @TaskAction
  def run() {
    scripts.each { script ->
      ant.sql(
        driver: driver,
        url: url,
        userid: userid,
        password: password,
        delimiter: ";"
      ) {
        classpath(path: project.configurations.runtime.asPath)
        fileset(dir: "src/main/resources/db/test-data", includes: script)
      }
    }
  }
}
