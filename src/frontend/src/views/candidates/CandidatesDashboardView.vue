<template>
    <v-card flat>
      <h2>Candidatos</h2>
      <v-text-field
        v-model="search"
        append-inner-icon="fas fa-search"
        label="Pesquisar"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="candidates"
        :search="search"
        item-key="id"
        no-data-text="Nenhum candidato a apresentar."
      >
      </v-data-table>
    </v-card>
</template>
  
<script setup lang="ts">
  import type CandidateDto from '@/models/candidates/CandidateDto';
  import RemoteServices from '@/services/RemoteServices';
  import { reactive, ref } from 'vue';
  
  let search = ref('');
  const headers = [
    { title: 'ID', value: 'id', sortable: true, filterable: false },
    { title: 'Nome', value: 'name', sortable: true, filterable: true },
    { title: 'Email',
      value: 'email',
      sortable: true,
      filterable: true,
      groupable: true,
    },
    
    // TODO: maybe add another column with possible actions? (edit / delete)
  ];
  
  let candidates: CandidateDto[] = reactive([]);
  
  RemoteServices.getCandidates().then((data) => {
    candidates.push(...data);
  });
</script>
  