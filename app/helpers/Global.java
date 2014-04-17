package helpers;

import java.util.Date;

import controllers.Ingester;
import play.*;

public class Global extends GlobalSettings {
	Thread synchThread = new Thread(new Runnable() {

		@Override
		public void run() {
			while (true) {
				Ingester ingest = new Ingester();
				ingest.getEntitiesSince(new Date());
				ingest.getEventsSince(new Date());
				try {
					Thread.sleep(60 * 1000 * 15);
				} catch (InterruptedException e) {
					return;
				}
			}

		}
	});

	public void onStart(Application app) {
		synchThread.run();
	}

	public void onStop(Application app) {
		synchThread.interrupt();
	}

}
