
import java.util.Date;

import controllers.Ingester;
import play.*;

public class Global extends GlobalSettings {
	protected static volatile boolean stoppingTime = false;
	protected static Thread synchThread = new Thread(new Runnable() {

		@Override
		public void run() {
			while (!stoppingTime) {
				Ingester ingest = new Ingester();
				
				try{
					ingest.getEntitiesSince(new Date());
				}catch(Exception e){
					
				}
				try{
					ingest.getEventsSince(new Date());
				}catch(Exception e){
					
				}
				System.out.println("Pulling");
				int mins = Play.application().configuration().getInt("external.pull_int");
				try {
					Thread.sleep(60 * 1000 * mins);
				} catch (InterruptedException e) {
					return;
				}
			}

		}
	});

	@Override
	public void onStart(Application app) {
		System.out.println("Application Starting");
		synchThread.start();
	}

	@Override
	public void onStop(Application app) {
		stoppingTime = true;
		synchThread.interrupt();
	}

}
