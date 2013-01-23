(ns fd-blog.views.post
  (:use [noir.core :only [defpage defpartial] :as nc]
        [noir.response :only [redirect] :as nr]
        [hiccup.element :only [mail-to link-to]]
        [hiccup.page :only [include-css include-js html5]]
        [hiccup.form :only [form-to text-field label submit-button]])
  (:require [fd-blog.models.users :as users]))

(defpartial fd-text [placehold id]
  [:div {:class "control-group"}
   [:label {:class "control-label"} placehold]
   [:div {:class "controls"} (text-field {:type "text" :class "input-xlarge" :placeholder placehold} id)]])

(defpartial fd-navbar []
  [:div {:class "navbar navbar-static-top navbar-inverse"}
   [:div {:class "navbar-inner"}
    [:h2 (link-to {:class "brand"} "/" "FakeDrake")]]])

(defpartial sign-up [& defaults]
  [:div {:class "hero-unit"}
   [:h1 "Hello World!"]
   [:p "We are not available at the moment. Please sign up and I will let you know about anything new here! You can see who has already subscrubed "
    (link-to "/pre-subscribed" "here")]
   [:br]
   (form-to {:class "form-horizontal"} [:post "/"]
            (fd-text "Full Name" "yourname")
            (fd-text "e-mail" "yourmail")
            [:div {:class "controls"}
             (submit-button {:type "submit" :class "btn"} "Join!")])])

(defpartial registered [name mail]
  [:p
   [:span (format "Welcome %s!" name)]
   [:br]
   [:span "We will let you know when we have news"]])

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

(defn homepage [cont]
  (html5
   [:body
    [:div {:class "container"}
     (fd-navbar)
     cont
     [:div {:class "footer well"} "Copyright &copy 2013 Chris 'fakedrake' Perivolaropoulos"]]
    (include-css "/css/bootstrap.min.css")
    (include-js "/js/bootstrap.min.js")
    (include-js "http://code.jquery.com/jquery-latest.js")]))

(defpage  "/" []
  (homepage (sign-up)))

(defpage [:post "/"] {:keys [yourname yourmail]}
  (users/save yourname yourmail)
  (homepage (registered yourname yourmail)))

(defpage "/pre-subscribed" []
  (homepage (presubscribed-users)))

(defpage "/delete/:id" {:keys [id]}
  (users/delete id)
  (redirect "/pre-subscribed"))
