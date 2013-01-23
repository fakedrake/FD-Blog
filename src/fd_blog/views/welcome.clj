(ns fd-blog.views.welcome
  (:require [fd-blog.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to fd-blog"]))
