package example

trait Scheduler
trait DailyScheduler extends Scheduler
trait HourlyScheduler extends Scheduler

trait ISchedulerSettings[S <: Scheduler] {
  val starTime: String
  def endTime: String
}

abstract class SchedulerSettings[S <: Scheduler] (val key: String) extends ISchedulerSettings [S]

object SchedulerSettings extends App {
  def apply[S <: Scheduler](key: String) = new SchedulerSettings[S](key) {
    override val starTime = "always the same"
    override def endTime = {
      key match {
        case (k:String) if k == "daily" => "1 day later read from configuration settings"
        case _                          => "1 hour later read from configuration settings"
      }
    }
  }

  def processHourlySettings[S <: HourlyScheduler](d: SchedulerSettings[S]) = d
  def processDailySettings[S <: DailyScheduler](d: SchedulerSettings[S]) = d

  def showSettings[S <: Scheduler](d: SchedulerSettings[S]) = {
    println("SHOWWWWW TIMEEEEEE")
    println(d.starTime)
    println(d.endTime)
  }


  // Main

  showSettings(SchedulerSettings.processHourlySettings(SchedulerSettings[HourlyScheduler]("daily")))
  showSettings(SchedulerSettings.processDailySettings(SchedulerSettings[DailyScheduler]("hourly")))

  // Error
  showSettings(SchedulerSettings.processDailySettings(SchedulerSettings[HourlyScheduler]("whatever")))
}

