package xyz.kyngs.simpleevent4j;

class EventSystemTest {

    public static void main(String[] args) throws InterruptedException {
        EventSystem system = new EventSystem();
        system.registerListener(String.class, System.out::println);
        system.fireEvent("This is an example string.");
        system = new EventSystem(1);
        system.registerListener(Integer.class, System.out::println);
        system.fireEvent(666);
        Thread.sleep(10); //It may take some time for the executor to take care of the work
    }

}