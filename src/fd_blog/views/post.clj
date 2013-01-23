(ns fd-blog.views.post
  (:use [noir.core :only [defpage defpartial] :as nc]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [include-css include-js html5]]
        [hiccup.form :only [form-to text-field label submit-button]])
  (:require [fd-blog.models.users :as users]))

;; Elements
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

;; Partials
(defpartial registered [name mail]
  [:p
   [:span (format "Welcome %s!" name)]
   [:br]
   [:span "We will let you know when we have news"]])

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

;; Pages
(defpage  "/" []
  (homepage (sign-up)))

(defpage [:post "/"] {:keys [yourname yourmail]}
  (users/save yourname yourmail)
  (homepage (registered yourname yourmail)))
