module com.piikii.application {
	exports com.piikii.application.port.input;
	exports com.piikii.application.port.output;
	exports com.piikii.application.domain.model;

	requires kotlin.stdlib;
	requires spring.context;
}
