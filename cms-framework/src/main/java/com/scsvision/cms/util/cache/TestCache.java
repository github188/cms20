/**
 * 
 */
package com.scsvision.cms.util.cache;

/**
 * @author MIKE
 *
 */
public class TestCache {

	public static void spyTest() throws Exception {
		long begin = System.currentTimeMillis();

		Runnable runPut = new Runnable() {

			public void run() {
				String tName = Thread.currentThread().getName();
				for (int i = 0; i < 10000; i++) {
					SpyMemcacheUtil.put(tName + i, "1234567890", 3600);
				}

			}
		};

		Thread[] tsPut = new Thread[8];

		for (int i = 0; i < tsPut.length; i++) {
			Thread t = new Thread(runPut, "Thread-" + i);
			tsPut[i] = t;
			t.start();
		}

		for (int i = 0; i < tsPut.length; i++) {
			tsPut[i].join();
		}

		// ----------------GET
		Runnable runGet = new Runnable() {

			public void run() {
				String tName = Thread.currentThread().getName();
				for (int i = 0; i < 10000; i++) {
					System.out.println(SpyMemcacheUtil.get(tName + i));
				}

			}
		};

		Thread[] tsGet = new Thread[8];
		for (int i = 0; i < tsGet.length; i++) {
			Thread t = new Thread(runGet, "Thread-" + i);
			tsGet[i] = t;
			t.start();
		}

		for (int i = 0; i < tsGet.length; i++) {
			tsGet[i].join();
		}

		long end = System.currentTimeMillis();

		System.out.println("SpyMemcached use " + (end - begin) + "ms");

		SpyMemcacheUtil.shutdown(1000);
	}

	public static void xTest() throws Exception {
		long begin = System.currentTimeMillis();

		Runnable runPut = new Runnable() {

			public void run() {
				String tName = Thread.currentThread().getName();
				for (int i = 0; i < 10000; i++) {
					XMemcacheUtil.put(tName + i, "1234567890", 3600);
				}

			}
		};

		Thread[] tsPut = new Thread[8];

		for (int i = 0; i < tsPut.length; i++) {
			Thread t = new Thread(runPut, "Thread-" + i);
			tsPut[i] = t;
			t.start();
		}

		for (int i = 0; i < tsPut.length; i++) {
			tsPut[i].join();
		}

		// ----------------GET
		Runnable runGet = new Runnable() {

			public void run() {
				String tName = Thread.currentThread().getName();
				for (int i = 0; i < 10000; i++) {
					System.out.println(XMemcacheUtil.get(tName + i));
				}

			}
		};

		Thread[] tsGet = new Thread[8];
		for (int i = 0; i < tsGet.length; i++) {
			Thread t = new Thread(runGet, "Thread-" + i);
			tsGet[i] = t;
			t.start();
		}

		for (int i = 0; i < tsGet.length; i++) {
			tsGet[i].join();
		}

		long end = System.currentTimeMillis();

		System.out.println("XMemcached use " + (end - begin) + "ms");
		XMemcacheUtil.shutdown();
	}
	
	public static void memcacheTest() throws Exception {
		long begin = System.currentTimeMillis();

		Runnable runPut = new Runnable() {

			public void run() {
				String tName = Thread.currentThread().getName();
				for (int i = 0; i < 10000; i++) {
					MemcacheUtil.put(tName + i, "1234567890", 3600);
				}

			}
		};

		Thread[] tsPut = new Thread[8];

		for (int i = 0; i < tsPut.length; i++) {
			Thread t = new Thread(runPut, "Thread-" + i);
			tsPut[i] = t;
			t.start();
		}

		for (int i = 0; i < tsPut.length; i++) {
			tsPut[i].join();
		}

		// ----------------GET
		Runnable runGet = new Runnable() {

			public void run() {
				String tName = Thread.currentThread().getName();
				for (int i = 0; i < 10000; i++) {
					System.out.println(MemcacheUtil.get(tName + i));
				}

			}
		};

		Thread[] tsGet = new Thread[8];
		for (int i = 0; i < tsGet.length; i++) {
			Thread t = new Thread(runGet, "Thread-" + i);
			tsGet[i] = t;
			t.start();
		}

		for (int i = 0; i < tsGet.length; i++) {
			tsGet[i].join();
		}

		long end = System.currentTimeMillis();

		System.out.println("Memcached use " + (end - begin) + "ms");
	}

	public static void main(String[] args) throws Exception {
		spyTest();
	}
}
