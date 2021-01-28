package io.openems.edge.meter.ttn;

import java.util.Base64;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.Designate;

import com.google.gson.Gson;

import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.meter.api.MeterType;
import io.openems.edge.meter.api.SymmetricMeter;

@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "meter.ttn", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE, //
		property = { //
				EventConstants.EVENT_TOPIC + "=" + EdgeEventConstants.TOPIC_CYCLE_BEFORE_PROCESS_IMAGE }//
)
public class TtnMeter extends AbstractOpenemsComponent implements OpenemsComponent, EventHandler, SymmetricMeter {

	private Config config = null;
	private MqttClient ttnClient = null;
	private int currentValue = 0;

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		;

		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}
	}

	public TtnMeter() {
		super(//
				OpenemsComponent.ChannelId.values(), //
				SymmetricMeter.ChannelId.values(), //
				ChannelId.values());
	}

	@Activate
	void activate(ComponentContext context, Config config) {
		super.activate(context, config.id(), config.alias(), config.enabled());
		this.config = config;

		String broker = "ssl://eu.thethings.network";
		String clientId = "OpenEMS";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			ttnClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName(config.appId());
			connOpts.setPassword(config.appKey().toCharArray());
			connOpts.setCleanSession(true);
			ttnClient.connect(connOpts);

			ttnClient.subscribe("+/devices/" + config.deviceId() + "/up", new IMqttMessageListener() {
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					try {
						String value = new String(message.getPayload());
						TtnMessage parsedMessage = new Gson().fromJson(value, TtnMessage.class);
						byte[] decoded = Base64.getDecoder().decode(parsedMessage.getPayload_raw());

						StringBuffer buffer = new StringBuffer();

						for (int i = 0; i < decoded.length; i++) {
							byte beit = decoded[i];
							String string = Integer.toHexString(Byte.toUnsignedInt(beit));
							buffer.append(string);
						}

						currentValue = Integer.parseInt(buffer.toString(), 16);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();

		try {
			ttnClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleEvent(Event event) {
		if (!this.isEnabled()) {
			return;
		}
		switch (event.getTopic()) {
		case EdgeEventConstants.TOPIC_CYCLE_BEFORE_PROCESS_IMAGE:
			channel(SymmetricMeter.ChannelId.ACTIVE_POWER).setNextValue(currentValue);
			break;
		}
	}

	@Override
	public String debugLog() {
		return null;
	}

	@Override
	public MeterType getMeterType() {
		return this.config.type();
	}
}
