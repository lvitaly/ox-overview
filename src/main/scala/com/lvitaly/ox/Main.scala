import ox.*
import scala.concurrent.duration.*

@main def main1(): Unit =
  supervised {
    val f1 = fork {
      sleep(2.seconds)
      1
    }

    val f2 = fork {
      sleep(1.second)
      2
    }

    val res = (f1.join(), f2.join())
    println(res)
  }


/*@main def main2(): Unit =
  fork {
    forever {
      try
        val m = nextMessage()
        processMessage(m)
      catch
        case e: Exception => // NonFatal(e) =>
          logger.error(e)
          redeliver
    }
  }*/