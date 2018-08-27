package mul

import play.api.libs.json._
import chisel3._
import chisel3.experimental.MultiIOModule
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}


class Mul extends Module {
	val io=IO(new Bundle{
		val a1=Input(UInt(8.W))
		val a2=Input(UInt(8.W))
		val out=Output(UInt(16.W))
	})
	io.out := io.a1 *io.a2
}

class MulTester(c: Mul) extends PeekPokeTester(c) {
	for {
		i<-0 until 15
		j<-0 until 15
    }{
    	poke(c.io.a1,i)
    	poke(c.io.a2,j)
    	expect(c.io.out,i*j)
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
