module com.piikii.adapter.in.http {
	requires com.piikii.application;

	requires spring.web;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires kotlin.stdlib;

	opens com.piikii.adapter.input.http to spring.core, spring.beans;
}
