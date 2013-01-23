(ns fd-blog.models.users
  (:use [monger.core :only [] :as mg]
        [monger.collection :only [insert find-maps] :as mc])
  (:import [org.bson.types ObjectId]))

(mg/connect!)
(mg/set-db! (mg/get-db "fdblog"))

(defn save [name email]
  (insert "presubscribed-users" {:_id (ObjectId.) :name name :email email}))

(defn names []
  (map #(get-in % [:name]) (find-maps "presubscribed-users")))

(defn emails []
  (map #(get-in % [:email]) (find-maps "presubscribed-users")))

(defn all-users []
  (find-maps "presubscribed-users"))

(defn delete [id]
  (mc/remove-by-id "presubscribed-users" (ObjectId. id)))
