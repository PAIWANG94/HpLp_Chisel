package mul

import play.api.libs.json._
import chisel3._
import chisel3.experimental.MultiIOModule
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}
import scala.io.Source



class Mul extends Module {
	val io=IO(new Bundle{
		val a1=Input(UInt(8.W))
		val a0=Input(UInt(8.W))
		val out=Output(UInt(16.W))
	})
	io.out := io.a1 *io.a0
}

class MulTester(c: Mul) extends PeekPokeTester(c) {
  val source: String = Source.fromFile("/home/paiche/HpLp_Chisel/Python/mulfile.json").getLines.mkString
  val json: JsValue = Json.parse(source)
  val jsonString =Json.stringify(json)
  val jsonS =Json.parse(jsonString)
  val result = json.as[List[Int]]
  val a1= json("a1")
  val a0=json("a0")
  val out=json("out")
  for{
		i<-0 until 3
    }{
      poke(c.io.a1,a1(i).as[Int])
    	poke(c.io.a0,a0(i).as[Int])
    	expect(c.io.out,out(i).as[Int])
    }
class MulSpec extends FlatSpec with Matchers {
  behavior of "Mul"

  it should "test correctly for every i/o combination with verilator" in {
    val args = Array("--backend-name", "verilator")
    iotesters.Driver.execute(args, () => new Mul) { c =>
      new MulTester(c)
    } should be (true)
  }
  it should "test correctly for every i/o combination with firrtl" in {
    val args = Array("--backend-name", "firrtl")
    iotesters.Driver.execute(args, () => new Mul) { c =>
      new MulTester(c)
    } should be (true)
  }
}

}
