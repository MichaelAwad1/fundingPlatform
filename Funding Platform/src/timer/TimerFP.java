package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import application.FPApplication;
public class TimerFP {
	
	
		public static void main(String[] args) throws InterruptedException
		{
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("Running: " + new java.util.Date());
					FPApplication app = new FPApplication();
					app.setSucccessAndFail();
					System.out.println("All Funding requests that reached the end date either marked as successful or failed");
				}
			}, 0, 86400000);
		}
	}