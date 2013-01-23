;; (ns fd-blog.views.user-table
;;   (:use [noir.core :only [defpage defpartial] :as nc]
;;         [noir.response :as nr]
;;         [hiccup.element :only [mail-to link-to]]
;;   (:require [fd-blog.models.users :as users]
;;             [fd-blog.views.post :as po])
;;   )
(ns fd-blog.views.usertable
  (:use [noir.core :only [defpage defpartial] :as nc]
        [noir.response :as nr]
        [hiccup.element :only [mail-to link-to]]
        [hiccup.page :only [include-css include-js html5]]
        [hiccup.form :only [form-to text-field label submit-button]])
  (:require [fd-blog.models.users :as users]
            [fd-blog.views.post :as po]))


(defpartial presubscribed-user [user]
  (let [name (get-in user [:name])
        email (get-in user [:email])
        id (get-in user [:_id])]
    [:tr
     [:td name]
     [:td (mail-to email)]
     [:td (link-to {:class "btn btn-danger"} (str "/delete/" id) [:i {:class "icon-trash icon-white"}])]]))

(defpartial presubscribed-users []
  [:table {:class "table table-hover"}
   [:tr
    [:th "Full Name"]
    [:th "E-mail"]
    [:th "Delete"]]
   (map presubscribed-user (users/all-users))])

(defpage "/pre-subscribed" []
  (po/homepage (presubscribed-users)))

(defpage "/delete/:id" {:keys [id]}
  (users/delete id)
  (nr/redirect "/pre-subscribed"))
