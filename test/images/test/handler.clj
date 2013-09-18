(ns images.test.handler
  (:use clojure.test
        midje.sweet
        ring.mock.request
        images.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

(fact
  "filename extension"
  (content-subtype "file.k") => "k"
  (content-subtype "file.jpg") => "jpg"
  (content-subtype "file.JPG") => "jpg"
  (content-subtype "path/with.dots/to/file.and.moar.dots.jpg") => "jpg")
