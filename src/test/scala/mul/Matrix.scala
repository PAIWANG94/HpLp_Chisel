package mul 

import chisel3._
import chisel3.iotesters.{PeekPokeTester, Driver}
import scala.collection.mutable.ArrayBuffer

class MatMult extends Module {
  val io = IO(new Bundle {
    val matA   = Input(Vec(15, UInt(32.W)))
    val matB   = Input(Vec(10, UInt(32.W)))
    val load  = Input(Bool())
    val matC = Output(Vec(6, UInt(32.W)))
    val valid = Output(Bool())
  })
  var sum = UInt(32.W)
  val matC = new ArrayBuffer[UInt]()

  for(i <- 0 until 6) {                
       matC += 0.asUInt(32.W)
  }

  when (io.load) {
    for(i <- 0 until 3) {
        for(j <- 0 until 2) {
            sum = 0.asUInt(32.W)
            for(k <- 0 until 5)
            {
                sum = sum + io.matA(i*5+k)*io.matB(k*2+j)

            }
            matC(i*2 + j) = sum
        }
    }
  io.valid := true.B
  } .otherwise {
    io.valid := false.B     
  }   

  val outputMat = Vec(matC)
  io.matC := outputMat   

}
