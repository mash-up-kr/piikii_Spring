module com.piikii.adapter.output.postgresql {
	requires com.piikii.application;
	requires spring.context;
//	requires jakarta.persistence;
//	requires spring.data.jpa;

	opens com.piikii.adapter.output.postgresql to spring.core;
}
