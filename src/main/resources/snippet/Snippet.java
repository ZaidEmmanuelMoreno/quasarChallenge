package snippet;

public class Snippet {
	runtime: java11
	env: standard
	instance_class: F1
	handlers:
	  - url: .*
	    script: auto
	automatic_scaling:
	  min_idle_instances: automatic
	  max_idle_instances: automatic
	  min_pending_latency: automatic
	  max_pending_latency: automatic
	network: {}
}

