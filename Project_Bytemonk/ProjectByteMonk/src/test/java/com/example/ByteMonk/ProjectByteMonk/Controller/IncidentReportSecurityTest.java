package com.example.ByteMonk.ProjectByteMonk.Controller;

import com.example.ByteMonk.ProjectByteMonk.Service.IncidentReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



    @WebMvcTest(IncidentReportController.class)
    class IncidentReportSecurityTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private IncidentReportService service;

        @Test
        @WithAnonymousUser
        void testUnauthenticatedAccessToGetAllIncidents() throws Exception {

            mockMvc.perform(get("/api/incident/getAllIncidents")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser(username = "username", roles = {"USER"})
        void testAuthenticatedAccessToGetAllIncidents() throws Exception {
            mockMvc.perform(get("/api/incident/getAllIncidents")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

