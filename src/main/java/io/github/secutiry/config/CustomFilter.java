package io.github.secutiry.config;

import io.github.secutiry.domain.security.CustomAuthentication;
import io.github.secutiry.domain.security.IdentificacaoUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomFilter extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException
	{
		String secret = request.getHeader("x-secret");

		if (secret != null && secret.equals("secr3t"))
		{
			IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
				"id-secreto",
				"Muito Secreto",
				"x-secret",
				List.of("USER")
			);

			Authentication authentication = new CustomAuthentication(identificacaoUsuario);

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);

	}
}
