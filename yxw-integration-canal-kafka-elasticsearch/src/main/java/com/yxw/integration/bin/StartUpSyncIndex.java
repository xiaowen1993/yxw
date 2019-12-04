package com.yxw.integration.bin;

import com.yxw.integration.task.taskitem.CunsumerRunnable;
import com.yxw.integration.task.taskitem.ProducerRunnable;

public class StartUpSyncIndex {

	public static void main(String[] args) {

		ProducerRunnable producerRunnable = new ProducerRunnable();
		producerRunnable.main(args);

		CunsumerRunnable cunsumerRunnable = new CunsumerRunnable();
		cunsumerRunnable.main(args);
	}
}
