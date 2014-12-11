package me.masahito.sample.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.zeromq.ZMQ;
import java.util.stream.IntStream;

public class EchoClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(EchoClient.class);
	private static final Marker MARKER = MarkerFactory.getMarker("EchoClient");

	public static void main (String[] args){
		try(
				final ZMQ.Context context = ZMQ.context(1);
				final ZMQ.Socket socket = context.socket(ZMQ.REQ)
		) {
			socket.connect(Utils.address);
			LOGGER.info(MARKER,"start: {}",  Utils.address);

			IntStream.range(0, 10).boxed().forEach(i -> {
				final String msg = String.format("msg %s", i);
				socket.send(msg);
				LOGGER.info(MARKER,"Sending: {}", msg);

				final byte[] msg_in = socket.recv();
				LOGGER.info(MARKER, "Receive: {}", new String(msg_in));
			});

		} catch (Exception e) {
			LOGGER.warn(MARKER, "{}", e);
		}




	}
}
