<template>
  <v-card flat>
    <h2>Salas</h2>
    <v-text-field
      v-model="search"
      append-inner-icon="fas fa-search"
      label="Pesquisar"
      single-line
      hide-details
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="rooms"
      :search="search"
      multi-sort
      locale="pt-PT"
      loading="A carregar salas.."
      no-data-text="Sem salas a apresentar."
      no-results-text="Nenhuma sala corresponde aos critérios indicados"
    >
      <template v-slot:item="{ item }">
        <!-- I'm sorry for the quality of this snippet, I just couldn't 
              get it working any other way and I've already spent way too
              much time on this, really -->
        <tr>
          <td style="padding-left: 1%" >{{ item.raw.id }}</td>
          <td>{{ item.raw.name }}</td>
          <td>{{ item.raw.building }}</td>
          <td>{{ item.raw.floor }}</td>
          <td>{{ item.raw.available ? "Sim" : "Não" }}</td>
        </tr>
      </template>
    </v-data-table>
  </v-card>
</template>

<script setup lang="ts">
import type RoomDto from '@/models/rooms/RoomDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
const headers = [
  { title: 'ID', value: 'id', key: 'id', sortable: true, filterable: false },
  { title: 'Nome', value: 'name', key: 'name', sortable: true, filterable: true },
  { title: 'Edifício', value: 'building', key: 'building', sortable: true, filterable: true },
  { title: 'Andar', value: 'floor', key: 'floor', sortable: true, filterable: true},
  { title: 'Disponível', value: 'available', key: 'available', sortable: true, filterable: true},
];

let rooms: RoomDto[] = reactive([]);

RemoteServices.getRooms().then((data) => {
  rooms.push(...data);
});

</script>
