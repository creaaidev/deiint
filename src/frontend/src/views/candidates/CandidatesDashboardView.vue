<!-- Add data table for candidates and edit an delete buttons, in a different way than before -->
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
        multi-sort
        locale="pt-PT"
        loading="A carregar candidatos..."
        no-data-text="Nenhum candidato a apresentar."
        no-results-text="Nenhum candidato corresponde aos critérios indicados"
      >
        <!-- Edit and Delete Buttons,  -->
        <template v-slot:item="{ item }">
          <tr>
            <!-- I'm sorry for the quality of this snippet, I just couldn't 
                  get it working any other way and I've already spent way too
                  much time on this, really -->
            <td style="padding-left: 1%" >{{ item.raw.id }}</td>
            <td>{{ item.raw.name }}</td>
            <td>{{ item.raw.email }}</td>
            <td>
              <v-btn
                color="primary"
                @click="editCandidate(item.raw.id)"
              >
                <v-icon>fas fa-cog</v-icon>
              </v-btn>
              <v-btn
                color="error"
                @click="deleteCandidate(item.raw.id)"
              >
                <v-icon>fas fa-trash</v-icon>
              </v-btn>
            </td>
          </tr>
        </template>
      </v-data-table>
    </v-card>
</template>
  
<script setup lang="ts">
  import type CandidateDto from '@/models/candidates/CandidateDto';
  import RemoteServices from '@/services/RemoteServices';
  import { reactive, ref } from 'vue';
  
  import { useRouter } from 'vue-router';

  let search = ref('');
  const headers = [
    { title: 'ID', value: 'id', key: 'id', sortable: true, filterable: false },
    { title: 'Nome', value: 'name', key: 'name', sortable: true, filterable: true },
    { title: 'Email',
      value: 'email',
      key: 'email',
      sortable: true,
      filterable: true,
      groupable: false,
    },
    { title: "Ações", value: "actions", sortable: false, filterable: false },
  ];
  
  let candidates: CandidateDto[] = reactive([]);
  
  RemoteServices.getCandidates().then((data) => {
    candidates.push(...data);
  });

  const deleteCandidate = (id: number) => {
  RemoteServices.deleteCandidate(id).then(() => {

    // TODO: Check if this is correct or can be done in another way
    candidates.splice(candidates.findIndex((candidate) => candidate.id === id), 1);

    // TODO: change to better alerts; Alert = true;
    alert('Candidato removido com sucesso!');
  });
};

// Use router to navigate to edit page
const router = useRouter();

const editCandidate = (id: number) => {
  router.push({ name: 'candidates-edit', params: { id: id } });
};

</script>
  