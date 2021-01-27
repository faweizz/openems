package io.openems.edge.meter.ttn;

public class TtnMessage {

	private String app_id;
	private String dev_id;
	private String hardware_serial;
	private int port;
	private int counter;
	private String payload_raw;
	private TtnMetadata metadata;

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getDev_id() {
		return dev_id;
	}

	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}

	public String getHardware_serial() {
		return hardware_serial;
	}

	public void setHardware_serial(String hardware_serial) {
		this.hardware_serial = hardware_serial;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getPayload_raw() {
		return payload_raw;
	}

	public void setPayload_raw(String payload_raw) {
		this.payload_raw = payload_raw;
	}

	public TtnMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(TtnMetadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((app_id == null) ? 0 : app_id.hashCode());
		result = prime * result + counter;
		result = prime * result + ((dev_id == null) ? 0 : dev_id.hashCode());
		result = prime * result + ((hardware_serial == null) ? 0 : hardware_serial.hashCode());
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
		result = prime * result + ((payload_raw == null) ? 0 : payload_raw.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TtnMessage other = (TtnMessage) obj;
		if (app_id == null) {
			if (other.app_id != null)
				return false;
		} else if (!app_id.equals(other.app_id))
			return false;
		if (counter != other.counter)
			return false;
		if (dev_id == null) {
			if (other.dev_id != null)
				return false;
		} else if (!dev_id.equals(other.dev_id))
			return false;
		if (hardware_serial == null) {
			if (other.hardware_serial != null)
				return false;
		} else if (!hardware_serial.equals(other.hardware_serial))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		if (payload_raw == null) {
			if (other.payload_raw != null)
				return false;
		} else if (!payload_raw.equals(other.payload_raw))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}
