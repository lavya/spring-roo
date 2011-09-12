package org.springframework.roo.addon.layers.repository.jpa;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.shell.CliAvailabilityIndicator;
import org.springframework.roo.shell.CliCommand;
import org.springframework.roo.shell.CliOption;
import org.springframework.roo.shell.CommandMarker;

/**
 * Commands for the JPA repository add-on.
 * @author Stefan Schmidt
 * @since 1.2.0
 */
@Component
@Service
public class RepositoryJpaCommands implements CommandMarker {
	
	@Reference private RepositoryJpaOperations repositoryJpaOperations;
	
	@CliAvailabilityIndicator("repository jpa")
	public boolean isRepositoryCommandAvailable() {
		return repositoryJpaOperations.isRepositoryCommandAvailable();
	}
	
	@CliCommand(value = "repository jpa", help = "Adds @RooRepositoryJpa annotation to target type") 
	public void repository(
		@CliOption(key = "interface", mandatory = true, help = "The java interface to apply this annotation to") JavaType interfaceType,
		@CliOption(key = "class", mandatory = false, help = "Implementation class for the specified interface") JavaType classType,
		@CliOption(key = "entity", unspecifiedDefaultValue = "*", optionContext = "update,project", mandatory = false, help = "The domain entity this repository should expose") JavaType domainType) {
		
		if (classType == null) {
			classType = new JavaType(interfaceType.getFullyQualifiedTypeName() + "Impl");
		}
		repositoryJpaOperations.setupRepository(interfaceType, classType, domainType);
	}
}