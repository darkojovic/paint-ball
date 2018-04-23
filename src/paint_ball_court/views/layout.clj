(ns paint-ball-court.views.layout
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]))

(defn common [activeTab & content]
  "Common html on all pages: header, footer, css, js"
  (html5
   [:head
    [:title "PaintBall Darko"]
    (include-css "https://fonts.googleapis.com/css?family=Roboto:400,100,300,700,900")
    (include-css "/css/animate.css")
    (include-css "/css/icomoon.css")
    (include-css "/css/bootstrap.css")
    (include-css "/css/superfish.css")
    (include-css "/css/style.css")
    (include-js "/js/modernizr-2.6.2.min.js")]
   [:body
    [:div
     {:id "fh5co-wrapper"}
     [:div
      {:id "fh5co-page"}
      [:div
       {:id "fh5co-header"}
       [:header
        {:id "fh5co-header-section"}
        [:div
         {:class "container"}
         [:div
          {:class "nav-header"}
          [:a {:class "js-fh5co-nav-toggle fh5co-nav-toggle" :href "/"} "<i></i>"]
          [:h1 {:id "fh5co-logo"}
           [:a {:href "/"} "Paint<span>Ball</span>"]]
          [:nav
           {:role "navigation" :id "fh5co-menu-wrap"}
           [:ul
            {:class "sf-menu" :id "fh5co-primary-menu"}
            [:li
             (if (= activeTab 0) {:class "active"})
             [:a {:href "/"} "Početna"]]
            [:li
             (if (= activeTab 1) {:class "active"})
             [:a {:href "/courts-all"} "Tereni"]]
            [:li
             (if (= activeTab 2) {:class "active"})
             [:a {:href "/reservations-all"} "Rezervacije"]]]]]]]]
      content
      [:footer
       {:id "footer"}
       [:div
        {:class "container"}
        [:div
         {:class "row copy-right"}
         [:div
          {:class "col-md-6 col-md-offset-3 text-center"}
          [:p
           {:class "fh5co-social-icons"}
           "Copyright Darko 2018 <br/> <a href=\"/\">PaintBall Darko</a>"]]]]]]]]
   (include-js "/js/jquery.min.js")
   (include-js "/js/jquery.easing.1.3.js")
   (include-js "/js/bootstrap.min.js")
   (include-js "/js/jquery.waypoints.min.js")
   (include-js "/js/jquery.stellar.min.js")
   (include-js "/js/hoverIntent.js")
   (include-js "/js/superfish.js")
   (include-js "/js/main.js")
   (include-js "/js/validator.js")))

