package me.masahito.sample.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.zeromq.ZMQ;

public class EchoServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);
	private static final Marker MARKER = MarkerFactory.getMarker("EchoServer");

	public static void main (String[] args){
		try(
			final ZMQ.Context context = ZMQ.context(1);
			final ZMQ.Socket socket = context.socket(ZMQ.REP)
		) {
			socket.bind(Utils.address);
			LOGGER.info(MARKER, "start: {}", Utils.address);
			while(true) {
				final byte[] msg = socket.recv();
				LOGGER.info(MARKER, "Received: {}", new String(msg));
				socket.send(msg);
			}
		} catch (Exception e) {
			LOGGER.warn(MARKER, "{}", e);
		}
	}
}
