(ns paint-ball-court.views.pages
  (:use [hiccup.core :only (h)])
  (:require [paint-ball-court.views.layout :as layout]
            [paint-ball-court.models.paint-ball-db :as db]
            [hiccup.form :refer :all]))

(defn index []
  (layout/common 0
                 [:div
                  {:class "fh5co-hero"}
                  [:div {:class "fh5co-overlay"}]
                  [:div
                   {:class                         "fh5co-cover"
                    :data-stellar-background-ratio "0.5"
                    :style                         "background-image: url(images/home-image.jpg);"}
                   [:div
                    {:class "desc animate-box"}
                    [:div
                     {:class "container"}
                     [:div
                      {:class "row"}
                      [:div
                       {:class "col-md-7"}
                       [:h2 "Dobrodošli!"]
                       [:p "<span>PaintBall klub Darko Vam želi prijatan dan na poslu.</span>"]]]]]]]

                 [:div
                  {:class "fh5co-lightgray-section"
                   :id    "fh5co-schedule-section"}
                  [:div
                   {:class "container"}
                   [:div
                    {:class "row"}
                    [:div
                     {:class "col-md-8 col-md-offset-2"}
                     [:div
                      {:class "heading-section text-center animate-box"}
                      [:h2 "Administracija"]
                      [:p "Administracija terena i rezervacija"]]]]
                   [:div
                    {:class "row text-center"}
                    [:div
                     {:class "col-md-12 schedule-container"}
                     [:div
                      {:class "schedule-content active"}
                      [:div
                       {:class "col-md-6 col-sm-6"}
                       [:a
                        {:href "/courts-all"}
                        [:div
                         {:class "program program-schedule"}
                         [:img {:src   "images/court.jpg"
                                :style "height:100px;"}]
                         [:h3 "Tereni"]]]]]]]]]))

(defn courts-all []
  (layout/common 1
                 [:div
                  {:class                         "fh5co-parallax"
                   :style                         "background-image: url(images/home-image-3.jpg); height: 240px;"
                   :data-stellar-background-ratio "0.5"}
                  [:div {:class "overlay"}]
                  [:div
                   {:class "container"}
                   [:div
                    {:class "row"}
                    [:div
                     {:class "col-md-8 col-md-offset-2 col-sm-12 col-sm-offset-0 col-xs-12 col-xs-offset-0 text-center fh5co-table"}
                     [:div
                      {:class "fh5co-intro fh5co-table-cell animate-box"}
                      [:p "Administracija terena"]]]]]]
                 [:div
                  {:id "fh5co-team-section"}
                  [:div
                   {:class "container"}
                   [:div
                    {:class "row"}
                    [:div
                     {:class "col-md-8 col-md-offset-2"}
                     [:div
                      {:class "heading-section text-center animate-box"}
                      [:h2 "Tereni"]]]]
                   [:div
                    {:class "row text-center"}
                    (for [court (db/all-courts)]
                      [:div
                       {:class "col-md-4 col-sm-6"}
                       [:div
                        {:class "team-section-grid"
                         :style "background-image: url(images/trainer-2.jpg);"}
                        [:div
                         {:class "overlay-section"}
                         [:div
                          {:class "desc"}
                          [:h3 (:name court)]
                          [:span (str "Kapacitet: " (:capacity court))]
                          [:span (str "Cena: " (:price court))]
                          [:img {:src (:image court)}]
                          [:p (:description court)]]]]])]]]))