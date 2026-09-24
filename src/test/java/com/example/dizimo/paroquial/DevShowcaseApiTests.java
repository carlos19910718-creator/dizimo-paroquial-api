package com.example.dizimo.paroquial;

import com.devshowcase.api.model.Profile;
import com.devshowcase.api.model.Technology;
import com.devshowcase.api.repository.ProfileRepository;
import com.devshowcase.api.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DevShowcaseApiTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private TechnologyRepository technologyRepository;

	@Test
	void shouldCreateAndFindProfile() throws Exception {
		mockMvc.perform(post("/api/profiles")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"name":"Ana Silva","email":"ana@example.com","bio":"Backend developer","githubUrl":"https://github.com/ana"}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber());

		Long profileId = profileRepository.findByEmail("ana@example.com").orElseThrow().getId();
		mockMvc.perform(get("/api/profiles/{id}", profileId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Ana Silva"))
				.andExpect(jsonPath("$.projects", hasSize(0)));
	}

	@Test
	void shouldCreateListAndUseTechnologyInProject() throws Exception {
		Profile profile = new Profile();
		profile.setName("Bruno Lima");
		profile.setEmail("bruno@example.com");
		profile = profileRepository.save(profile);

		Technology technology = new Technology();
		technology.setName("Java");
		technology = technologyRepository.save(technology);

		mockMvc.perform(post("/api/technologies")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{" + "\"name\":\"Spring Boot\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/technologies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));

		mockMvc.perform(post("/api/projects")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{" +
						"\"title\":\"Portfolio API\",\"description\":\"API de showcase\"," +
						"\"projectUrl\":\"https://github.com/bruno/portfolio\"," +
						"\"profileId\":" + profile.getId() + ",\"technologyIds\":[" + technology.getId() + "]}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("Portfolio API"))
				.andExpect(jsonPath("$.technologies[0].name").value("Java"));

		mockMvc.perform(get("/api/projects"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void shouldRejectBlankProjectTitle() throws Exception {
		mockMvc.perform(post("/api/projects")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{" +
						"\"title\":\"\",\"description\":\"Descricao\",\"profileId\":1," +
						"\"technologyIds\":[1]}"))
				.andExpect(status().isBadRequest());
	}
}