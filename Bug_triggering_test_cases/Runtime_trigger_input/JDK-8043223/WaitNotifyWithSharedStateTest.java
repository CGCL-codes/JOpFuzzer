
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class WaitNotifyWithSharedStateTest {
	private static void printUsage() {
		System.err.println("One parameter is required: number of producer threads");
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			printUsage();
			System.exit(1);
		}

		int numberOfThreads = 0;
		try {
			numberOfThreads = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			printUsage();
			System.exit(2);
		}

		EventListElement initialEvent = new EventListElement(null);

		EventConsumer eventConsumerThread = new EventConsumer(initialEvent);
		eventConsumerThread.start();

		EventManager eventListenerManager = new EventManager(initialEvent);
		for (int i = 1; i <= numberOfThreads; i++) {
			EventProducer eventProducerThread = new EventProducer("producer " + i + " ", eventListenerManager);
			//mark the producer threads as daemons in order to exit the test app after the error 
			eventProducerThread.setDaemon(true);
			eventProducerThread.start();
		}
	}
}

class Event {
	private String message;
	private int priority;

	public Event(String message, int priority) {
		this.message = message;
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

class EventListElement {
	private Event event = null;
	// reference to the next EventListElement
	private EventListElement next = null;

	public EventListElement(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return this.event;
	}

	public synchronized EventListElement getNext() throws InterruptedException {
		if (this.next == null) {
			//wait till the next event is registered
			this.wait();
		}

		// store the reference to a local variable, before cleaning up
		EventListElement result = this.next;

		// clean up - make the garbage collector's work easier
		this.next = null;
		this.event = null;

		return result;
	}

	public synchronized void addNext(EventListElement element) {
		this.next = element;
		this.notifyAll();
	}

}

class EventProducer extends Thread {
	//the standard message probability is in fact: (STD_MESSAGE_PROBALITY-1)/STD_MESSAGE_PROBALITY  
	private static final int STD_MESSAGE_RATIO = 100 * 1000 * 1000;
	
	String threadId;
	Random rnd = new Random();
	EventManager eventListenerManager;

	public EventProducer(String threadId, EventManager eventListenerManager) {
		this.threadId = threadId;
		this.eventListenerManager = eventListenerManager;
	}

	public void run() {
		while (true) {
			eventListenerManager.addEvent(generateEvent());
			//slow down the current thread
			Thread.yield();
		}
	}

	private Event generateEvent() {
		String messageText = threadId + " " + System.currentTimeMillis();
		//standard message priority
		int priority = 0;
		if (rnd.nextInt(STD_MESSAGE_RATIO) == 0) {
			//important message priority
			priority = 1;
		}
		return new Event(messageText, priority);
	}
}

class EventConsumer extends Thread {
	// reference to current (or initial) event queue element.
	private EventListElement currentEvent = null;

	public EventConsumer(EventListElement initialEvent) {
		currentEvent = initialEvent;
	}

	public void run() {
		try {
			Event event;
			while (true) {
				try {
					currentEvent = currentEvent.getNext();
				} catch (InterruptedException e) {
					return;
				}
				event = currentEvent.getEvent();

				processEvent(event);

				if (isInterrupted()) {
					return;
				}
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private void processEvent(Event event) {
		//only important messages are printed
		if (event.getPriority() > 0) {
			System.out.println(event.getMessage());
		}
	}
}

class EventManager {
	private AtomicReference<EventListElement> fEventQueueRef = new AtomicReference<EventListElement>();

	public EventManager(EventListElement initialEvent) {
		fEventQueueRef.set(initialEvent);
	}

	public void addEvent(Event event) {
		EventListElement newElement = new EventListElement(event);
		EventListElement prevElement = fEventQueueRef.getAndSet(newElement);
		prevElement.addNext(newElement);
	}
}

