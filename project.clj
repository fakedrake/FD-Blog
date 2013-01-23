(defproject fd-blog "0.1.0"
            :description "A nice little blogie in clojure"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [noir "1.3.0-beta3"]
                           [com.novemberain/monger "1.4.2"]]
	    :plugins [[lein-ring "0.8.2"]]
            :ring {:handler fd-blog.server/handler}
            :main fd-blog.server)
