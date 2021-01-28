package io.openems.edge.meter.ttn;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import io.openems.edge.meter.api.MeterType;

@ObjectClassDefinition(//
		name = "TTN Meter", //
		description = "Implements a meter to consume data from the things network.")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "meter0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	String webconsole_configurationFactory_nameHint() default "Meter LoRa [{id}]";

	@AttributeDefinition(name = "Meter-Type", description = "Grid, Production (=default), Consumption")
	MeterType type() default MeterType.PRODUCTION;
	
	@AttributeDefinition(name = "TTN app id", description = "Application id of the ttn network")
	String appId() default "";
	
	@AttributeDefinition(name = "TTN app key", description = "Application key of the ttn network")
	String appKey() default "";
	
	@AttributeDefinition(name = "Device id", description = "Device id to listen to")
	String deviceId() default "";
}