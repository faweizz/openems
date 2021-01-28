package io.openems.edge.meter.ttn;

import io.openems.edge.common.test.AbstractComponentConfig;
import io.openems.edge.meter.api.MeterType;

@SuppressWarnings("all")
public class MyConfig extends AbstractComponentConfig implements Config {

	protected static class Builder {
		private String id;
//		private String setting0;

		private Builder() {
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

//		public Builder setSetting0(String setting0) {
//			this.setting0 = setting0;
//			return this;
//		}

		public MyConfig build() {
			return new MyConfig(this);
		}
	}

	/**
	 * Create a Config builder.
	 * 
	 * @return a {@link Builder}
	 */
	public static Builder create() {
		return new Builder();
	}

	private final Builder builder;

	private MyConfig(Builder builder) {
		super(Config.class, builder.id);
		this.builder = builder;
	}

	@Override
	public MeterType type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String appId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String appKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deviceId() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public String setting0() {
//		return this.builder.setting0;
//	}

}