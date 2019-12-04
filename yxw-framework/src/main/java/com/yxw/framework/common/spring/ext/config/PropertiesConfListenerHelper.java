package com.yxw.framework.common.spring.ext.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yxw.framework.config.SystemConfig;

public class PropertiesConfListenerHelper {
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
	private WatchService ws;
	private String listenerPath;

	private PropertiesConfListenerHelper(String path) {
		try {
			ws = FileSystems.getDefault().newWatchService();
			this.listenerPath = path;
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		fixedThreadPool.execute(new Listner(ws, this.listenerPath));
	}

	public static void addListener(String path) throws IOException, URISyntaxException {
		PropertiesConfListenerHelper propertiesConfListenerHelper = new PropertiesConfListenerHelper(path);
		Path p = Paths.get(path);
		p.register(propertiesConfListenerHelper.ws, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE);
	}

}

class Listner implements Runnable {
	private WatchService service;
	private String rootPath;

	public Listner(WatchService service, String rootPath) {
		this.service = service;
		this.rootPath = rootPath;
	}

	public void run() {
		try {
			while (true) {
				WatchKey watchKey = service.take();
				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
				for (WatchEvent<?> event : watchEvents) {
					if (event.kind().name().equals("ENTRY_MODIFY")) {
						SystemConfig.loadSystemConfig();
					}

				}
				watchKey.reset();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				service.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}