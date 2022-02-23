(ns clojure-api.system
  (:require
    [com.stuartsierra.component :as component]
    [clojure-api.schema :as schema]
    [clojure-api.server :as server]
    [clojure-api.db :as db]))

(defn new-system
  []
  (merge (component/system-map)
         (schema/new-schema-provider)
         (server/new-server)
         (db/new-db)))