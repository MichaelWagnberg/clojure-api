(defproject clojure-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.9.0"]
                 [metosin/reitit "0.5.12"]
                 [metosin/muuntaja "0.6.8"]
                 [com.layerware/hugsql-core "0.5.1"]
                 [com.layerware/hugsql-adapter-clojure-java-jdbc "0.5.1"]
                 [http-kit "2.5.3"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.5.jre7"]
                 [com.mchange/c3p0 "0.9.5.2"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]
                 [org.graphqlize/graphqlize "0.1.0-alpha-2"]
                 [org.flatland/ordered "1.15.10"]
                 [hikari-cp "2.13.0"]
                 [com.walmartlabs/lacinia "0.30.0"]
                 [com.walmartlabs/lacinia-pedestal "0.10.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [environ "1.1.0"]
                 [cheshire "5.8.0"]
                 [io.aviso/logging "0.2.0"]
                 [com.stuartsierra/component "0.3.2"]]
  :main ^:skip-aot clojure-api.core
  :target-path "target/%s"
  :resource-paths ["sql" "resources"]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
