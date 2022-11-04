/*
 * Copyright 2018 http4s.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.http4s
package scalatags

import _root_.scalatags.Text
import cats.data.NonEmptyList
import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.Status.Ok
import org.http4s.headers.`Content-Type`

class ScalatagsSuite extends CatsEffectSuite {
  private val testCharsets = NonEmptyList(
    Charset.`ISO-8859-1`,
    List(
      Charset.fromString("Windows-1251").toOption,
      Charset.fromString("GB2312").toOption,
      Charset.fromString("Shift-JIS").toOption,
      Charset.fromString("Windows-1252").toOption,
    ).flatten,
  )

  private def testBody() = {
    import Text.all

    all.div()(
      all.p()(all.raw("this is my testBody"))
    )
  }

  private def testDoctypeBody() = {
    import Text.all._
    import Text.tags2.title
    import Text.all.doctype

    doctype("html")(
      html(
        lang := "en",
        head(
          title("http4s-scalatags")
        ),
        body(this.testBody()),
      )
    )
  }

  test("TypedTag encoder should return Content-Type text/html with proper charset") {
    testCharsets.map { implicit cs =>
      val headers = EntityEncoder[IO, Text.TypedTag[String]].headers
      assertEquals(headers.get[`Content-Type`], Some(`Content-Type`(MediaType.text.html, Some(cs))))
    }
  }

  test("Doctype encoder should return Content-Type text/html with proper charset") {
    testCharsets.map { implicit cs =>
      val headers = EntityEncoder[IO, Text.all.doctype].headers
      assertEquals(headers.get[`Content-Type`], Some(`Content-Type`(MediaType.text.html, Some(cs))))
    }
  }

  test("TypedTag encoder should render the body") {
    implicit val cs: Charset = Charset.`UTF-8`
    val resp = Response[IO](Ok).withEntity(testBody())
    EntityDecoder
      .text[IO]
      .decode(resp, strict = false)
      .value
      .assertEquals(Right("<div><p>this is my testBody</p></div>"))
  }

  test("Doctype encoder should render the body") {
    implicit val cs: Charset = Charset.`UTF-8`

    val resp = Response[IO](Ok).withEntity(testDoctypeBody())

    EntityDecoder
      .text[IO]
      .decode(resp, strict = false)
      .value
      .assertEquals(Right("<!DOCTYPE html><html lang=\"en\"><head><title>http4s-scalatags</title></head><body><div><p>this is my testBody</p></div></body></html>"))
  }
}
