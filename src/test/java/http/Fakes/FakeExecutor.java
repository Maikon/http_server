package http.fakes;

import http.Worker;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class FakeExecutor implements ExecutorService {
  private Runnable workerCalled;
  private boolean closed;

  public Runnable calledWith() {
    return workerCalled;
  }

  @Override
  public void execute(Runnable command) {
    closed = false;
    workerCalled = command;
  }

  public boolean calledWith(Worker worker) {
    return worker.equals(workerCalled);
  }

  @Override
  public void shutdown() {
    closed = true;
  }

  @Override
  public List<Runnable> shutdownNow() {
    return null;
  }

  @Override
  public boolean isShutdown() {
    return closed;
  }

  @Override
  public boolean isTerminated() {
    return false;
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return false;
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return null;
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return null;
  }

  @Override
  public Future<?> submit(Runnable task) {
    return null;
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
    return null;
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
    return null;
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
    return null;
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return null;
  }
}