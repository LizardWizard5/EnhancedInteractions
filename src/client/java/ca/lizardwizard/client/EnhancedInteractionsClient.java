package ca.lizardwizard.client;

import ca.lizardwizard.registers.RegisterEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class EnhancedInteractionsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(RegisterEntityTypes.ECHO_SHARD, ThrownItemRenderer::new);
	}
}