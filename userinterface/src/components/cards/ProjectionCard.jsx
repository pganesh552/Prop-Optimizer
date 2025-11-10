import React from "react";

const ProjectionCard = ({ projection }) => (
  <div className="p-4 bg-blue-100 rounded shadow">
    <h2 className="font-bold">Projection</h2>
    <pre>{JSON.stringify(projection, null, 2)}</pre>
  </div>
);

export default ProjectionCard;
