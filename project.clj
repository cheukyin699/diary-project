(defproject journal-project "0.1.0-SNAPSHOT"
  :description "A simple web server hosting markdown files by date"
  :url "https://github.com/cheukyin699/journal-project"
  :license {:name "GNU Public License v3"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [markdown-clj "1.0.2"]]
  :main jp.core)
