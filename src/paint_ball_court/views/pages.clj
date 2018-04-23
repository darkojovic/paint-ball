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
                      [:div {:class "col-md-6 col-sm-6"}
                       [:a {:href "/courts-all"}
                        [:div {:class "program program-schedule"}
                         [:img {:src   "images/court.jpg" :style "height:100px;"}]
                         [:h3 "Tereni"]]]]
                      [:div {:class "col-md-6 col-sm-6"}
                       [:a {:href "/reservations-all"}
                        [:div {:class "program program-schedule"}
                         [:img {:src   "images/reservation.jpg" :style "height:100px;"}]
                         [:h3 "Rezervacije"]]]]]]]]]))

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
                      [:h2 "Tereni"]
                      [:a {:href "/courts-new" :class "btn btn-primary"} "Novi teren"]]]]
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
                          [:p (:description court)]
                          [:a
                           {:href  (str "/courts-edit/" (h (:id court)))
                            :class "btn btn-primary"}
                           "Izmeni"]
                          [:br]
                          [:br]
                          [:a
                           {:href  (str "/courts-delete/" (h (:id court)))
                            :class "btn btn-danger"}
                           "Obriši"]]]]])]]]))

(defn courts-edit [& [id error]]
  (let [court (if
                (not (nil? id))
                (db/find-court id)
                nil)]
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
                        [:p (if (nil? id) "Novi teren" "Izmena terena")]]]]]]
                   [:div
                    {:class "fh5xo-contact"}
                    [:div
                     {:class "container"}
                     (form-to
                      [:post "/courts-save"
                       :name "court-form"
                       :id   "court-form"]
                      [:p {:class "alert alert-danger;" :style "color:red" :id "alert alert-danger"} error]
                      [:div
                       {:class "row"}
                       [:div
                        {:class "col-md-9 animate-box"}
                        [:div
                         {:class "row"}
                         [:div
                          {:class "col-md-1"}
                          [:label {:for "id"} "Id"]]
                         [:div
                          {:class "col-md-11"}
                          (text-field
                           {:readonly "true"
                            :type     "text"
                            :class    "form-control"
                            :id       "id"
                            :name     "id"
                            :required "required"}
                           "id" (:id court))]]
                        [:br]

                        [:div
                         {:class "row"}
                         [:div
                          {:class "col-md-1"}
                          [:label {:for "name"} "Naziv"]]
                         [:div
                          {:class "col-md-11"}
                          (text-field
                           {:type        "text"
                            :class       "form-control"
                            :id          "name"
                            :name        "name"
                            :placeholder "naziv"
                            :required    "required"}
                           "name" (:name court))]]
                        [:br]

                        [:div
                         {:class "row"}
                         [:div
                          {:class "col-md-1"}
                          [:label {:for "capacity"} "Kapacitet"]]
                         [:div
                          {:class "col-md-11"}
                          (text-field
                           {:type        "number"
                            :class       "form-control"
                            :id          "capacity"
                            :name        "capacity"
                            :placeholder "kapacitet"
                            :required    "required"}
                           "capacity" (:capacity court))]]
                        [:br]

                        [:div
                         {:class "row"}
                         [:div
                          {:class "col-md-1"}
                          [:label {:for "image"} "Slika"]]
                         [:div
                          {:class "col-md-11"}
                          (text-field
                           {:type        "text"
                            :class       "form-control"
                            :id          "image"
                            :name        "image"
                            :placeholder "putanja(images/... ili url)"}
                           "image" (:image court))]]
                        [:br]

                        [:div
                         {:class "row"}
                         [:div
                          {:class "col-md-1"}
                          [:label {:for "image"} "Opis"]]
                         [:div
                          {:class "col-md-11"}
                          (text-area
                           {:class "form-control" :id "description" :name "description" :placeholder "opis" :rows "7"} "description" (:description court))]]
                        [:br]

                        [:div
                         {:class "row"}
                         [:div
                          {:class "col-md-1"}
                          [:label {:for "price"} "Cena"]]
                         [:div
                          {:class "col-md-11"}
                          (text-field
                           {:type        "number"
                            :class       "form-control"
                            :id          "price"
                            :name        "price"
                            :placeholder "cena"
                            :required    "required"}
                           "price" (:price court))]]
                        [:br]
                        [:br]
                        [:div
                         {:class "row"}
                         [:a {:href "/courts-all" :class "btn btn-secondary"} "Nazad"]
                         (submit-button
                          {:class "btn btn-primary"} "Sačuvaj")
                         [:br]
                         [:br]
                         [:br]]]])]])))

(defn reservations-all []
  (layout/common 2
                 [:div
                  {:class "fh5co-parallax" :style "background-image: url(images/home-image-2.jpg); height: 240px;"
                   :data-stellar-background-ratio "0.5"}
                  [:div {:class "overlay"}]
                  [:div {:class "container"}
                   [:div {:class "row"}
                    [:div {:class "col-md-8 col-md-offset-2 col-sm-12 col-sm-offset-0 col-xs-12 col-xs-offset-0 text-center fh5co-table"}
                     [:div {:class "fh5co-intro fh5co-table-cell animate-box"}
                      [:p "Administracija rezervacija"]]]]]]
                 [:div {:id "fh5co-team-section"}
                  [:div {:class "container"}
                   [:div {:class "row text-center"}
                    (for [court (db/all-courts)]
                      [:div
                       [:div {:class "row"}
                        [:div {:class "col-md-8 col-md-offset-2"}
                         [:div {:class "heading-section text-center animate-box"}
                          [:h2 (:name court)]]]]
                       [:div {:class "row"}
                        [:table {:class "table"}
                         [:thead
                          [:tr
                           [:th "Datum"]
                           [:th "Vreme"]
                           [:th "Kontakt osoba"]
                           [:th "Telefon"]
                           [:th "Popust"]
                           [:th "Ukupna cena"]]]
                         (into [:tbody]
                               (for [reservation (db/reservations-by-court (:id court))]
                                 [:tr
                                  [:td (:date reservation)]
                                  [:td (:time reservation)]
                                  [:td (:name reservation)]
                                  [:td (:contact_number reservation)]
                                  [:td (:discount reservation)]
                                  [:td (:total_price reservation)]
                                 ]))]]])]]]))