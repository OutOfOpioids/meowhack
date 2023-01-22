package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.Wrapper;

import java.util.ArrayDeque;
import java.util.Queue;

public class ThreadManager extends Manager {
    private final ClientService clientService = new ClientService();

    // processes
    private static final Queue<Runnable> clientProcesses = new ArrayDeque<>();

    public ThreadManager() {
        super("ThreadManager");

        // start the client thread
        clientService.setName("meowhack-client-thread");
        clientService.setDaemon(true);
        clientService.start();
    }

    public static class ClientService extends Thread implements Wrapper {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {

                    // check if the mc world is running
                    if (nullCheck()) {

                        // run and remove the latest service
                        if (!clientProcesses.isEmpty()) {
                            clientProcesses.poll().run();
                        }

                        // module onThread
                        for (Module module : getMeowhack().getModuleManager().getAllModules()) {

                            // check if the module is safe to run
                            if (nullCheck() || getMeowhack().getNullSafeFeatures().contains(module)) {

                                // check if module should run
                                if (module.isEnabled()) {

                                    // run
                                    try {
                                        module.onThread();
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                        }

                        // manager onThread
                        for (Manager manager : getMeowhack().getAllManagers()) {

                            // check if the manager is safe to run
                            if (nullCheck() || getMeowhack().getNullSafeFeatures().contains(manager)) {

                                // run
                                try {
                                    manager.onThread();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }
                    }

                    // give up thread resources
                    else {
                        Thread.yield();
                    }

                } catch(Exception exception) {
                        exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Submit a new runnable to the thread
     * @param in The runnable
     */
    public void submit(Runnable in) {
        clientProcesses.add(in);
    }

    /**
     * Gets the main client thread
     * @return The main client thread
     */
    public ClientService getService() {
        return clientService;
    }
}